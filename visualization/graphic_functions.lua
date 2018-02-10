-- Component function, meant to be slotted in as an objects 'draw' function
function renderPawn(self,color,accent)
  if not color or not accent then
    color = {255,255,50}
    accent = {255,255,150}
  end
  local old_color = {love.graphics.getColor()}
  love.graphics.setColor(color)
  love.graphics.circle('fill', self.displayPosition.x, self.displayPosition.y, 22, 16)
  love.graphics.setColor(accent)
  love.graphics.circle('line', self.displayPosition.x, self.displayPosition.y, 22, 16)
  love.graphics.circle('line', self.displayPosition.x, self.displayPosition.y, 11, 8)

  love.graphics.setColor(old_color)
end

-- Not meant to be bound to an object, maybe it could be if we wanted to go that route?
function renderBoardSquare(x,y,color)
  if not color then
    color = {255,255,255}
  end
  local old_color = {love.graphics.getColor()}
  love.graphics.setColor(color)
  love.graphics.rectangle(
  'fill',
   (x-1) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.x,
   (y-1) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.y,
   BOARD_SETTINGS.square.length,
   BOARD_SETTINGS.square.length)

   love.graphics.setColor(old_color)
end

-- Not meant to be bound to an object
function renderBoard()
  for x=1,10 do
    for y=1,10 do
      local color = kCOLOR_SQUARE_LIGHT
      if x % 2 == y % 2 then
        color = kCOLOR_SQUARE_DARK
      end
      renderBoardSquare(x,y,color)
    end
  end
end
