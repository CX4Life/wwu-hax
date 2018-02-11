import numpy as np
import os
from history_to_train import WIN_DIR, LOSS_DIR

def np_array_from_filename(filename_with_path):
    ret = []
    long_array = np.genfromtxt(filename_with_path, dtype=float, delimiter=',')
    for array in long_array:
        ret.append(array.reshape((8,8)))
    return ret


def get_total_x_y():
    every_x = []
    every_y = []
    for win_filename in os.listdir(WIN_DIR):
        w_board_states = np_array_from_filename(win_filename)
        every_x.extend(w_board_states)
        every_y.extend([1.0] * len(w_board_states))
    for loss_filename in os.listdir(LOSS_DIR):
        l_board_states = np_array_from_filename(loss_filename)
        every_x.extend(l_board_states)
        every_y.extend([0.0] * len(l_board_states))
    return every_x, every_y


if __name__ == '__main__':
    np_array_from_filename('win_loss/loss/1518331391.631597loss.txt')
