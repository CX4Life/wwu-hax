require('color')

function Particle(x,y,color,accent)
  local part = {}

  addXYComponent(part,x,y)

  addLiveFunction(part,'draw',function(self)
    self.radius = self.radius * 1.1

    local dx,dy = actualPosition(self.x, self.y)
    love.graphics.circle('line',dx,dy, self.radius, math.floor(love.math.random()*5 + 3) )
    love.graphics.circle('line',dx,dy, self.radius, math.floor(love.math.random()*5 + 2) )

    local angle = math.pi * math.random() * 2
    love.graphics.circle('fill',dx+math.sin(angle)*self.radius/2,dy+math.cos(angle)*self.radius/2,20,math.floor(love.math.random()*5 + 2))

    if self.radius > 500 then
      removeLiveFunction(self,'draw')
    end
  end)

  part.radius = 16

  return part
end

function particleExplosion(self)
  self.radius = self.radius + 1
  print(self.radius)

  love.graphics.circle('line', self.x, self.y, self.radius, 5)
end
