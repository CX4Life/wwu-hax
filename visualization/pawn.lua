require('graphic_functions')
require('color')

-- Creates a pawn object
function Pawn()
  local pawn = {}
  pawn.displayPosition = {}

  pawn.color = kCOLOR_BLACK
  pawn.accent_color = kCOLOR_BLACK_ACCENT
  
  addXYComponent(pawn)
  addXYComponent(pawn.displayPosition)
  addLiveFunction(pawn,'draw',renderPawn)
  addLiveFunction(pawn,'update',function()
    interpolateDisplayPosition(pawn)
  end)

  return pawn
end

function addXYComponent(self)
  self.x = 1
  self.y = 1
end

-- To bind to Pawn object
-- Renders a pawn at position x,y
function interpolateDisplayPosition(self)
  local actualPosition = {
    x = (self.x-1) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.x + BOARD_SETTINGS.square.length / 2,
    y = (self.y-1) * BOARD_SETTINGS.square.length + BOARD_SETTINGS.offset.y + BOARD_SETTINGS.square.length / 2
  }

  -- TODO: make this interpolate rather than assign
  self.displayPosition.x = actualPosition.x
  self.displayPosition.y = actualPosition.y
end
