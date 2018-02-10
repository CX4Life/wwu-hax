require('graphic_functions')
require('color')

-- Creates a pawn object
function Pawn(id,x,y)
  local pawn = {}
  pawn.displayPosition = {}
  pawn.id = id
  pawn.draw_args = {}
  pawn.draw_args.color = kCOLOR_BLACK
  pawn.draw_args.accent = kCOLOR_BLACK_ACCENT

  if pawn.id > 11 then
    pawn.draw_args.color = kCOLOR_RED
    pawn.draw_args.accent = kCOLOR_RED_ACCENT
  end

  addXYComponent(pawn,x,y)
  addXYComponent(pawn.displayPosition)
  addLiveFunction(pawn,'draw',renderPawn)
  addLiveFunction(pawn,'update',function()
    interpolateDisplayPosition(pawn)
  end)

  return pawn
end

function addXYComponent(self,x,y)
  self.x = 1
  self.y = 1

  if x and y then
    self.x = x
    self.y = y
  end
end

-- To bind to Pawn object
-- Renders a pawn at position x,y
function interpolateDisplayPosition(self)
  local actualPosition = {
    x = (self.x) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.x + BOARD_SETTINGS.square.length / 2,
    y = (self.y) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.y + BOARD_SETTINGS.square.length / 2
  }

  -- TODO: make this interpolate rather than assign
  self.displayPosition.x = actualPosition.x
  self.displayPosition.y = actualPosition.y
end
