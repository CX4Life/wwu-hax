require('globals')

function parseActionList(file_contents)
  local split_lines = file_contents:split('\n')
  for i,v in ipairs(split_lines) do
    local split_command = v:split(' ')
    local type = 'move'
    local id = tonumber(split_command[1])
    local args = {}
    if not id then
      type = split_command[1]
      id = tonumber(split_command[2])
    else
      args = split_command[2]:split(',')
    end

    local action = {
      type,id,unpack(args)
    }

    ACTIONS[#ACTIONS+1] = action
  end
end

function executeAction(action)
  if action == nil then
    return
  end

  print(unpack(action))

  local type = action[1]
  local id = action[2]
  if type == 'move' then
    local x = action[3]
    local y = action[4]
    PAWNS[id]:setPosition(x,y)
    PRIORITY_OBJECT = PAWNS[id]
    whosturn = 'red'
    if id <= 11 then
      whosturn = 'black'
    end
  end

  if type == 'remove' then
    destroyPawn(id)
  end

  if type == 'king' then
    PAWNS[id]:king()
  end

  ACTION_INDEX = ACTION_INDEX + 1
end
