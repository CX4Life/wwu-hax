require('color')

local mouseDown = false
local useTouchScreen = false

function Button(x,y,text,settings,callback)
  local old_color = {love.graphics.getColor()}
  local width = 160
  local height = 64

  if not useTouchScreen then
    mx,my = love.mouse.getPosition()
  end

  -- Optional param finaggling
  if not callback then
    callback = settings
  else
    if settings.height then
      height = settings.height
    end

    if settings.width then
      width = settings.width
    end
  end

  if mx > x and mx < x + width and my > y and my < y + height then
    love.graphics.setColor(kCOLOR_UI)

    if mouseRelease then
      callback()
      if useTouchScreen then
        mx,my = 0,0
      end
      mouseRelease = false
    end

    if love.mouse.isDown(1) or love.touch.getTouches()[0] then
      love.graphics.setColor(kCOLOR_SQUARE_LIGHT)
    end
  else
    love.graphics.setColor(kCOLOR_UI_ACCENT)
  end

  love.graphics.rectangle('fill', x, y, width, height)

  love.graphics.setColor(0,0,0)
  love.graphics.print(text,x+12,y+8)
  love.graphics.rectangle('line', x, y, width, height)

  love.graphics.setColor(old_color)
end

function love.touchpressed(id, x, y, dx, dy, pressure)
  useTouchScreen = true
  mx,my = x,y
end

function love.mousemoved(x, y, dx, dy)
  useTouchScreen = false
end

function love.touchreleased(id, x, y, dx, dy, pressure)
  mouseRelease = true
end

function love.mousereleased(x, y, button, isTouch)
  if not isTouch then
    mouseRelease = true
  end
end
