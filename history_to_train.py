import os
import time

HISTORY_DIR = 'move_files/'
OUTPUT_DIR = 'win_loss/'
WIN_DIR = OUTPUT_DIR + 'win/'
LOSS_DIR = OUTPUT_DIR + 'loss/'
BLK = 2
RED = 1
K_BLK = 4
K_RED = 3


def winner_zero(list_of_states):
    ret = []
    for state in list_of_states:
        new_state = ','.join([x if x != '3' else '-1'for x in state])
        new_state = ''.join([x if x != '2' else '3' for x in new_state])
        new_state = ''.join([x if x != '4' else '-3' for x in new_state])
        ret.append(new_state + '\n')
    return ret


def loser_one(list_of_states):
    return winner_zero(list_of_states)


def winner_one(list_of_states):
    ret = []
    for state in list_of_states:
        new_state = ','.join([x if x != '1' else '-1' for x in state])
        new_state = ''.join([x if x != '3' else '1' for x in new_state])
        new_state = ''.join([x if x != '2' else '-3' for x in new_state])
        new_state = ''.join([x if x != '4' else '3' for x in new_state])
        ret.append(new_state + '\n')
    return ret


def loser_zero(list_of_states):
    return winner_one(list_of_states)


def write_winner(list_of_board_states, winner):
    now = str(time.time())
    if winner == 0:
        to_write = winner_zero(list_of_board_states)
    else:
        to_write = winner_one(list_of_board_states)
    with open(WIN_DIR + now + 'win.txt', 'w') as log:
        log.writelines(to_write)


def write_loser(list_of_board_states, winner):
    now = str(time.time())
    if winner == 0:
        to_write = loser_zero(list_of_board_states)
    else:
        to_write = loser_one(list_of_board_states)
    with open(LOSS_DIR + now + 'loss.txt', 'w') as log:
        log.writelines(to_write)


def write_win_loss_from_history(open_file):
    now = str(time.time())
    winner = open_file.readline().rstrip()
    list_of_lines = []
    for line in open_file:
        if line[:1] != '\n':
            list_of_lines.append(line.rstrip())
    open_file.close()
    even_lines = []
    odd_lines = []
    for num, line in enumerate(list_of_lines):
        if num % 2 == 0:
            even_lines.append(line)
        else:
            odd_lines.append(line)
    if winner is '0':
        write_winner(even_lines, 0)
        write_loser(odd_lines, 0)
    else:
        write_winner(odd_lines, 1)
        write_loser(even_lines, 1)


def main():
    every_history_file = os.listdir(HISTORY_DIR)
    for hist_file in every_history_file:
        with open(HISTORY_DIR + hist_file, 'r') as working_file:
            write_win_loss_from_history(working_file)


if __name__ == '__main__':
    main()
