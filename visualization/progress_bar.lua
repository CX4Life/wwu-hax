function ProgressBar(x,y,height,width,percent)
  local old_color = {love.graphics.getColor()}
  love.graphics.setColor(kCOLOR_UI_ACCENT)
  love.graphics.rectangle('fill', x, y, width, height)
  love.graphics.setColor(kCOLOR_UI)
  love.graphics.rectangle('fill', x, y, math.floor(width * percent), height)
  if percent > 0 then
    love.graphics.setColor(kCOLOR_BLACK_ACCENT)
    love.graphics.rectangle('fill', x + math.floor(width * percent) - height, y, height, height)
  end
  love.graphics.setColor(0,0,0)
  love.graphics.rectangle('line', x, y, width, height)

  love.graphics.setColor(old_color)
end
