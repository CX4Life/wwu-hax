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
    x = 32,
    y = 44
  },
  square = {
    length = 64
  }
}

local id_counter = 0

for y = 0,7 do
  for x = 0,7 do
    if x % 2 ~= y % 2 and y < 3 then
      Pawn(id_counter,x,y)
      id_counter = id_counter + 1
    end
    if x % 2 ~= y % 2 and y > 4 then
      Pawn(id_counter,x,y)
      id_counter = id_counter + 1
    end
  end
end

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
