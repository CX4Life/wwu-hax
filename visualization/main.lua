require('string_util')
require('live_objects')
require('graphic_functions')
require('color')
require('pawn')
require('playback')
require('button')
require('globals')
require('progress_bar')
require('ui')

love.window.setTitle('TensorFlow Plays Checkers')
love.graphics.setBackgroundColor(kCOLOR_BACKGROUND)
love.graphics.setNewFont(16)
love.window.setMode(1600,900,{resizable = true,minwidth = 964,minheight = 800,highdpi = true})

debugModeWrapper = {state}

function love.filedropped(file)
  INPUT_FILENAME = file:getFilename()
  INPUT_DATA = file:read()
  resetEverything()
end

-- Built in load function
function love.load(args)
  preserveArgs = args
  if not INPUT_FILENAME then
    INPUT_FILENAME = 'input.txt'
    if args[2] then
      INPUT_FILENAME = args[2]:split(' ')[1];
    end
  end

  if not INPUT_DATA then
    INPUT_DATA = love.filesystem.read(INPUT_FILENAME)
  end

  if INPUT_FILENAME then
    love.window.setTitle('TensorFlow Plays Checkers -- ' .. INPUT_FILENAME)
  end

  parseActionList(INPUT_DATA)
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

    if (ACTION_INDEX-1)/#ACTIONS >= 1 then
      playPause = 'Replay'
    end
  end

  local button_width = 240 + love.graphics.getWidth() / 8
  local ui_x = love.graphics.getWidth() - button_width - 32

  -- The 'key' field is only used to track when the button should highlight, actual keybinding is done in ui.lua
  Button(ui_x,BOARD_SETTINGS.offset.y,playPause..' (W)',{width = button_width,height = 96,key='w'},ui.play)
  Button(ui_x,BOARD_SETTINGS.offset.y+68*2,'Step << (Q)',{width = button_width/2,key='q'},ui.back)
  Button(ui_x + button_width/2,BOARD_SETTINGS.offset.y+68*2,'Step >> (E)',{width = button_width/2,key='e'},ui.step)
  Button(ui_x,BOARD_SETTINGS.offset.y+68*3,'Restart Playback (R)',{width = button_width, key='r'},ui.reset)

  renderTurnIndicator(ui_x,BOARD_SETTINGS.offset.y+68*7)
  ToggleButton(ui_x,BOARD_SETTINGS.offset.y+68*8,'Debug Mode (D)',{},debugModeWrapper)
  ProgressBar(BOARD_SETTINGS.offset.x, love.graphics.getHeight() - 64, 32, love.graphics.getWidth()-BOARD_SETTINGS.offset.x*2, (ACTION_INDEX-1)/#ACTIONS)
end
