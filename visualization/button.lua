require('color')

local mouseDown = false

function Button(x,y,text,callback)
  local old_color = {love.graphics.getColor()}
  local width = 128
  local height = 32
  local mx,my = love.mouse.getPosition()
  if mx > x and mx < x + width and my > y and my < y + height then
    love.graphics.setColor(kCOLOR_UI)

    if getMouseRelease() then
      callback()
    end

    if mouseDown then
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

function getMouseRelease()
  local mouseWasDown = mouseDown
  mouseDown = love.mouse.isDown(1)

  if not mouseDown and mouseDown ~= mouseWasDown then
    return true
  end

  return false
end
