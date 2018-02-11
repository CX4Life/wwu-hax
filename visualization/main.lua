require('string_util')
require('live_objects')
require('graphic_functions')
require('color')
require('pawn')
require('playback')
require('button')
require('globals')

love.window.setTitle('TensorFlow Plays Checkers')
love.graphics.setBackgroundColor(kCOLOR_BACKGROUND)
love.graphics.setNewFont(16)
love.window.setMode(1600,900,{resizable = true,minwidth = 800,minheight = 600,highdpi = true})

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
  renderBoard()
  runLiveFunction('draw')

  local playPause = 'Play'
  if playingBack then
    playPause = 'Pause'
  end

  Button(600,BOARD_SETTINGS.offset.y,playPause,{height = 128},function()
    playingBack = not playingBack
  end)

  Button(600,BOARD_SETTINGS.offset.y+44*3,'Restart Playback',function()
    resetEverything()
  end)

end
