require('color')

function Particle(x,y,color,accent)
  local part = {}

  part.tick = 0
  part.color = color
  part.accent = accent

  addXYComponent(part,x,y)

  part.fragments = {}

  for i=1,7 do
    part.fragments[i] = {}
    addXYComponent(part.fragments[i],x,y)
    part.fragments[i].vel = {}
    addXYComponent(part.fragments[i].vel,math.floor( (love.math.random()-0.5) * 20),-math.floor(love.math.random() * 20 + 15))
  end

  addLiveFunction(part,'draw',function(self)
    local old_color = {love.graphics.getColor()}
    self.radius = self.radius * 1.4

    local dx,dy = actualPosition(self.x, self.y)
    love.graphics.setColor(self.color)
    love.graphics.circle('line',dx,dy, self.radius, math.floor(love.math.random()*5 + 3) )
    love.graphics.setColor(self.accent)
    love.graphics.circle('line',dx,dy, self.radius, math.floor(love.math.random()*5 + 2) )

    --love.graphics.circle('fill',dx+math.sin(angle)*self.radius/2,dy+math.cos(angle)*self.radius/2,20,math.floor(love.math.random()*5 + 2))

    for i=1,#self.fragments do
      local f = self.fragments[i]
      love.graphics.setColor(self.color)
      love.graphics.circle('fill',dx + f.x,dy + f.y,20,math.floor(love.math.random()*5 + 5))
      f.x = f.x + f.vel.x
      f.y = f.y + f.vel.y

      f.vel.y = f.vel.y + 2
    end

    self.tick = self.tick + 1

    if self.tick > 60 * 10 then
      removeLiveFunction(self,'draw')
    end

    love.graphics.setColor(old_color)
  end)

  part.radius = 16

  return part
end

function particleExplosion(self)
  self.radius = self.radius + 1
  print(self.radius)

  love.graphics.circle('line', self.x, self.y, self.radius, 5)
end
