import os
import time

HISTORY_DIR = 'move_files/'
OUTPUT_DIR = 'win_loss/'
BLK = 2
RED = 1
K_BLK = 4
K_RED = 3


def write_winner(list_of_board_states):
    print('winner lines', len(list_of_board_states))


def write_loser(list_of_board_states):
    print('loser lines', len(list_of_board_states))



def write_win_loss_from_history(open_file):
    now = str(time.time())
    winner = open_file.readline().rstrip()
    list_of_lines = []
    for line in open_file:
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
        write_winner(even_lines)
        write_loser(odd_lines)
    else:
        write_winner(even_lines)
        write_loser(odd_lines)


def main():
    every_history_file = os.listdir(HISTORY_DIR)
    for hist_file in every_history_file:
        with open(HISTORY_DIR + hist_file, 'r') as working_file:
            write_win_loss_from_history(working_file)


if __name__ == '__main__':
    main()
