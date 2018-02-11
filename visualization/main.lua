require('string_util')
require('live_objects')
require('graphic_functions')
require('color')
require('pawn')
require('playback')
require('button')

love.window.setTitle('TensorFlow Plays Checkers')
love.graphics.setBackgroundColor(kCOLOR_BACKGROUND)
INPUT_FILENAME = 'input.txt'

BOARD_SETTINGS = {
  offset = {
    x = 32,
    y = 44
  },
  square = {
    length = 64
  }
}

delay_timer = 1
playingBack = false

-- Built in load function
function love.load(args)
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

  delay_timer = delay_timer - dt
  if delay_timer < 0 then
    executeAction(ACTIONS[ACTION_INDEX])
    delay_timer = 0.5
  end
end

-- Built in draw function
-- Draws to the screen every 1/60th of a second
function love.draw()
  renderBoard()
  runLiveFunction('draw')

  Button(600,BOARD_SETTINGS.offset.y,'Play',function()
    playingBack = true
  end)
  Button(600,BOARD_SETTINGS.offset.y+44*1,'Pause Playback',function()
    playingBack = false
  end)
  Button(600,BOARD_SETTINGS.offset.y+44*2,'Restart Playback',function()
    resetEverything()
  end)

end
