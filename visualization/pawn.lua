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
  addXYComponent(pawn.displayPosition,actualPosition(x,y))
  addLiveFunction(pawn,'draw',renderPawn)
  addLiveFunction(pawn,'update',function()
    interpolateDisplayPosition(pawn)
  end)

  pawn.destroy = function(self)
    removeLiveFunction(self,'draw')
    removeLiveFunction(self,'update')
  end

  PAWNS[pawn.id] = pawn
  print(pawn.id,PAWNS[pawn.id])

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
  local actual_x,actual_y = actualPosition(self.x,self.y)

  self.displayPosition.x = self.displayPosition.x + math.floor((actual_x - self.displayPosition.x)/2)
  self.displayPosition.y = self.displayPosition.y + math.floor((actual_y - self.displayPosition.y)/2)
end

function destroyPawn(id)
  if PAWNS[id] then
    PAWNS[id]:destroy()
  end
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
