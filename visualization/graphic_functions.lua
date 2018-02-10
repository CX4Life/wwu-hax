-- Component function, meant to be slotted in as an objects 'draw' function
function renderPawn(self)
  local color = self.draw_args.color
  local accent = self.draw_args.accent

  if not color or not accent then
    color = {255,255,50}
    accent = {255,255,150}
  end
  local old_color = {love.graphics.getColor()}
  love.graphics.setColor(color)
  love.graphics.circle('fill', self.displayPosition.x, self.displayPosition.y, BOARD_SETTINGS.square.length / 2 - 5, 18)
  love.graphics.setColor(accent)
  love.graphics.circle('line', self.displayPosition.x, self.displayPosition.y, BOARD_SETTINGS.square.length / 2 - 5, 18)
  love.graphics.circle('line', self.displayPosition.x, self.displayPosition.y, BOARD_SETTINGS.square.length / 4, 8)

  love.graphics.setColor(255,255,255)
  if self.id > 11 then
    love.graphics.setColor(0,0,0)
  end
  love.graphics.print(self.id,self.displayPosition.x-love.graphics.getFont():getWidth(self.id)/2, self.displayPosition.y-10)

  love.graphics.setColor(old_color)
end

-- Not meant to be bound to an object, maybe it could be if we wanted to go that route?
function renderBoardSquare(x,y,color)
  if not color then
    color = {255,255,255}
  end
  local old_color = {love.graphics.getColor()}
  local display_x = (x-1) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.x
  local display_y = (y-1) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.y
  love.graphics.setColor(color)
  love.graphics.rectangle(
  'fill',
   display_x,
   display_y,
   BOARD_SETTINGS.square.length,
   BOARD_SETTINGS.square.length)

   love.graphics.setColor(100,100,100)
   love.graphics.print(x .. ', ' .. y,display_x,display_y)

   love.graphics.setColor(old_color)
end

-- Not meant to be bound to an object
function renderBoard()
  for x=1,8 do
    for y=1,8 do
      local color = kCOLOR_SQUARE_LIGHT
      if x % 2 ~= y % 2 then
        color = kCOLOR_SQUARE_DARK
      end
      renderBoardSquare(x,y,color)
    end
  end
end
