ACTIONS = {
  --{'move',15,7,4},
  --{'move',10,6,3},
  --{'move',15,5,2},
  --{'remove',10}
}

ACTION_INDEX = 1

function executeAction(action)
  if action == nil then
    return
  end
  local type = action[1]
  local id = action[2]
  if type == 'move' then
    local x = action[3]
    local y = action[4]
    PAWNS[id]:setPosition(x,y)
  end

  if type == 'remove' then
    PAWNS[id]:destroy()
  end

  if type == 'king' then
    PAWNS[id]:king()
  end

  ACTION_INDEX = ACTION_INDEX + 1
end

function parseActionList(file_contents)
  local split_lines = file_contents:split('\n')
  for i,v in ipairs(split_lines) do
    print(v)
    local split_command = v:split(' ')
    local type = 'move'
    local id
    if not tonumber(split_command[1]) then
      type = split_command[1]
    end

    local action = {

    }
    --ACTIONS[#ACTIONS+1] = action
  end
end
