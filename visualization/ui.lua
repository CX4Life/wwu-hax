require('playback')

ui = {
  play = function()
    playingBack = not playingBack
    if (ACTION_INDEX-1)/#ACTIONS == 1 then
      resetEverything()
    end
  end,

  step = function()
    playingBack = false
    delay_timer = -1
  end,

  reset = function()
    resetEverything()
  end,

  back = function()
    playingBack = false
    local index = ACTION_INDEX-2
    if index < 0 then index = 0 end
    resetEverything()

    for i=1,index do
      executeAction(ACTIONS[i],true)
    end
  end,

  debug = function()
    debugModeWrapper.state = not debugModeWrapper.state
  end,

  faster = function()
    DELAY_TIMER_SET = DELAY_TIMER_SET - .05
    if DELAY_TIMER_SET < .05 then
      DELAY_TIMER_SET = .05
    end
  end,

  slower = function()
    DELAY_TIMER_SET = DELAY_TIMER_SET + .05
    if DELAY_TIMER_SET > 1 then
      DELAY_TIMER_SET = 1
    end
  end
}

keybind = {}
keybind['space'] = ui.play
keybind['w'] = ui.play
keybind['e'] = ui.step
keybind['q'] = ui.back
keybind['r'] = ui.reset
keybind['d'] = ui.debug
keybind['right'] = ui.step
keybind['left'] = ui.back
keybind['a'] = ui.slower
keybind['d'] = ui.faster

-- Built in function that's called every keypress
function love.keypressed(key, scancode, isrepeat)
  if keybind[scancode] then
    keybind[scancode]()
  end
end
