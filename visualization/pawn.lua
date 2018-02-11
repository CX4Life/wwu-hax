require('graphic_functions')
require('color')

PAWNS = {}

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

  pawn.destroy = function(self)
    removeLiveFunction(self,'draw')
    removeLiveFunction(self,'update')
  end

  PAWNS[pawn.id] = pawn

  return pawn
end

function addXYComponent(self,x,y)
  self.x = 1
  self.y = 1

  if x and y then
    self.x = x
    self.y = y
  end

  self.setPosition = function(self,x,y)
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

-- Create all the pawns
function initializePawns()
  local id_counter = 0
  for y = 0,7 do
    for x = 0,7 do
      if x % 2 ~= y % 2 and y < 3 then
        Pawn(id_counter,x,y)
        id_counter = id_counter + 1
      end
      if x % 2 ~= y % 2 and y > 4 then
        Pawn(id_counter,x,y)
        id_counter = id_counter + 1
      end
    end
  end
end
