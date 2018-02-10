require('string_util')
require('live_objects')
require('graphic_functions')
require('color')
require('pawn')

love.window.setTitle('TensorFlow Plays Checkers')
love.graphics.setBackgroundColor(kCOLOR_BACKGROUND)
INPUT_FILENAME = 'input.txt'

BOARD_SETTINGS = {
  offset = {
    x = 100,
    y = 32
  },
  square = {
    length = 54
  }
}

Pawn()

-- Built in load function
function love.load(args)
  INPUT_FILENAME = args[2]:split(' ')[1];
end

-- Built in update function
-- Executes approximately every 1/60th of a second
function love.update(dt)
  runLiveFunction('update',dt)
end

-- Built in draw function
-- Draws to the screen every 1/60th of a second
function love.draw()
  renderBoard()
  runLiveFunction('draw')
end
