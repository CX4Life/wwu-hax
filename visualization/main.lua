require('string_util')
require('live_objects')
require('graphic_functions')

love.window.setTitle('TensorFlow Plays Checkers')

INPUT_FILENAME = 'input.txt'

-- Built in load function
function love.load(args)
  INPUT_FILENAME = args[2]:split(' ')[1];

  print(unpack(LIVE_OBJECTS['draw']))
end

-- Built in update function
-- Executes approximately every 1/60th of a second
function love.update(dt)
  runLiveFunction('update',dt)
end

-- Built in draw function
-- Draws to the screen every 1/60th of a second
function love.draw()
  love.graphics.print('hello world')
  runLiveFunction('draw')
end
