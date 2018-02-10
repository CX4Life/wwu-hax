import random

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
        print('this dif', dif)
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


def main():
    curr = [[0,1,0,0],
            [1,0,0,0],
            [0,2,0,0],
            [2,0,0,0]]
    next = [[0,1,0,0],
            [0,0,0,0],
            [0,0,0,0],
            [2,0,1,0]]
    next2 = [[0,0,0,0],
             [1,0,1,0],
             [0,2,0,0],
             [2,0,0,0]]

    possible_states = [next, next2]
    diffed = pick_move_by_diff(curr, possible_states)
    if diffed is not None:
        print_selection_to_stdout(diffed)
    else:
        print_selection_to_stdout(pick_move_random(possible_states))


if __name__ == '__main__':
    main()
