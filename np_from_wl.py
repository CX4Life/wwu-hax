import numpy as np
import os
from history_to_train import WIN_DIR, LOSS_DIR


def np_array_from_filename(filename_with_path):
    ret = None
    long_array = np.genfromtxt(filename_with_path, dtype=float, delimiter=',')
    if type(long_array[0]) is not np.float64:
        for array in long_array:
            if ret is not None:
                np.concatenate((ret, array.reshape((8,8))), axis=0)
            else:
                ret = array.reshape((8,8))
    else:
        ret = long_array.reshape((8,8))
    return ret


def get_total_x_y():
    every_x = None
    every_y = None
    for win_filename in os.listdir(WIN_DIR):
        w_board_states = np_array_from_filename(WIN_DIR + win_filename)
        if every_x is not None:
            np.concatenate((every_x, w_board_states), axis=0)
        else:
            every_x = w_board_states
        if every_y is not None:
            np.concatenate((every_y, [1.0 *len(w_board_states)]))
        else:
            every_y = np.array([1.0 * len(w_board_states)])

    for loss_filename in os.listdir(LOSS_DIR):
        l_board_states = np_array_from_filename(LOSS_DIR + loss_filename)
        if every_x is not None:
            np.concatenate((every_x, l_board_states), axis=0)
        else:
            every_x = l_board_states
        if every_y is not None:
            np.concatenate((every_y, [1.0 *len(l_board_states)]))
        else:
            every_y = np.array([1.0 * len(l_board_states)])

    return every_x, every_y


if __name__ == '__main__':
    np_array_from_filename('win_loss/loss/1518345450.739979loss.txt')
