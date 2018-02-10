require('string_util')
require('live_objects')
require('graphic_functions')

love.window.setTitle('TensorFlow Plays Checkers')

INPUT_FILENAME = 'input.txt'

-- Built in load function
function love.load(args)
  INPUT_FILENAME = args[2]:split(' ')[1];
  addLiveFunction({x=200,y=200},'draw',renderPiece)
end

-- Built in update function
-- Executes approximately every 1/60th of a second
function love.update(dt)

end

-- Built in draw function
-- Draws to the screen every 1/60th of a second
function love.draw()
  love.graphics.print('hello world')
  print(unpack(LIVE_OBJECTS['draw'][1]))
  LIVE_OBJECTS['draw'][1]:draw()
end
