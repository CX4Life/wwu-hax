import random
import time
import subprocess


OUTPUT_DIR = 'move_files/'
INIT_STATE = [[0,1,0,1,0,1,0,1],
              [1,0,1,0,1,0,1,0],
              [0,1,0,1,0,1,0,1],
              [0,0,0,0,0,0,0,0],
              [0,0,0,0,0,0,0,0],
              [2,0,2,0,2,0,2,0],
              [0,2,0,2,0,2,0,2],
              [2,0,2,0,2,0,2,0]]


def diff_states(current, next):
    dif = 0
    for cur_row, next_row in zip(current, next):
        for cur_elem, next_elem in zip(cur_row, next_row):
            dif +=int(cur_elem) - int(next_elem)
    return abs(dif)


def pick_move_by_diff(current_state, set_of_states):
    max_so_far = -1
    state = None
    for new_state in set_of_states:
        dif = diff_states(current_state, new_state)
        if dif > max_so_far:
            max_so_far = dif
            state = new_state

    return state


def pick_move_random(set_of_states):
    return random.choice(set_of_states)


def print_selection_to_stdout(pick):
    for row in pick:
        line = ' '.join(str(x) for x in row)
        print(line)


def read_states_from_stdout(proc):
    states = []

    while True:
        state = []
        for i in range(8):
            line = proc.stdout.readline().rstrip()
            if line[:1] != '?'.encode():
                this_line = [int(chr(x)) for x in line if chr(x) is not ' ']
                state.append(this_line)
            else:
                return states
        states.append(state)


def pick_state(states):
    diffed = pick_move_by_diff(INIT_STATE, states)
    return diffed if diffed else pick_move_random(states)


def detect_win(states):
    if len(states) != 1:
        return False
    elif len(states[0][0]) != 8:
        return True
    return False


def print_selection_to_proc(proc, choice):
    whole_string = ''
    for line in choice:
        whole_string += ''.join([str(x) for x in line])
    whole_string += '\n'
    proc.stdin.write(whole_string.encode())
    proc.stdin.flush()


def write_game_history(history, winner):
    now = str(time.time())
    with open(OUTPUT_DIR + now + '.txt', 'w') as log:
        log.write(winner)
        for state in history:
            stringy = ''
            for i, line in enumerate(state):

                if i % 8 == 0:
                    stringy += '\n'
                stringy += ''.join([str(x) for x in line])
            log.write(stringy + '\n')


def play_game():
    history = [INIT_STATE * 5]
    proc = subprocess.Popen(['python3', 'random_states.py'], stdout=subprocess.PIPE, stdin=subprocess.PIPE)
    new_states = read_states_from_stdout(proc)
    if detect_win(new_states):
        winner = str(new_states[0][0][0])
        write_game_history(history, winner)

    choice = pick_state(new_states)
    history.append(choice)
    print_selection_to_proc(proc, choice)


if __name__ == '__main__':
    play_game()
