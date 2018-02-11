require('string_util')
require('live_objects')
require('graphic_functions')
require('color')
require('pawn')
require('playback')
require('button')
require('globals')
require('progress_bar')

love.window.setTitle('TensorFlow Plays Checkers')
love.graphics.setBackgroundColor(kCOLOR_BACKGROUND)
love.graphics.setNewFont(16)
love.window.setMode(1600,900,{resizable = true,minwidth = 800,minheight = 664,highdpi = true})

debugModeWrapper = {state}

-- Built in load function
function love.load(args)
  preserveArgs = args
  if args[2] then
    INPUT_FILENAME = args[2]:split(' ')[1];
  end
  parseActionList(love.filesystem.read(INPUT_FILENAME))

  initializePawns()
end

-- Built in update function
-- Executes approximately every 1/60th of a second
function love.update(dt)
  runLiveFunction('update',dt)

  DEBUG_MODE = debugModeWrapper.state

  if playingBack then
    delay_timer = delay_timer - dt
  end

  if delay_timer < 0 then
    executeAction(ACTIONS[ACTION_INDEX])
    delay_timer = 0.25
  end
end

-- Built in draw function
-- Draws to the screen every 1/60th of a second
function love.draw()
  BOARD_SETTINGS.square.length = love.graphics.getHeight() * .1

  local boardWidth = BOARD_SETTINGS.square.length * 8

  renderBoard()
  runLiveFunction('draw')

  local playPause = 'Play'
  if playingBack then
    playPause = 'Pause'
  end

  --local ui_x = boardWidth + BOARD_SETTINGS.offset.x + 64
  local ui_x = love.graphics.getWidth() - 160 - 32

  Button(ui_x,BOARD_SETTINGS.offset.y,playPause,{height = 128},function()
    playingBack = not playingBack
    if (ACTION_INDEX-1)/#ACTIONS == 1 then
      resetEverything()
    end
  end)

  Button(ui_x,BOARD_SETTINGS.offset.y+68*3,'Restart Playback',function()
    resetEverything()
  end)

  ToggleButton(ui_x,BOARD_SETTINGS.offset.y+68*4,'Debug Mode',{},debugModeWrapper)

  ProgressBar(BOARD_SETTINGS.offset.x, love.graphics.getHeight() - 64, 32, love.graphics.getWidth()-BOARD_SETTINGS.offset.x*2, (ACTION_INDEX-1)/#ACTIONS)

  renderTurnIndicator(ui_x,BOARD_SETTINGS.offset.y+68*6)
end
