-- Component function, meant to be slotted in as an objects 'draw' function
function renderPiece(self)
  love.graphics.rectangle('fill', self.x, self.y, 200, 200)
  self.x = self.x + 1
end
