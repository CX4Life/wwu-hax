import numpy as np


def np_array_from_line(filename_with_path):
    ret = []
    long_array = np.genfromtxt(filename_with_path, dtype=float, delimiter=',')
    for array in long_array:
        ret.append(array.reshape((8,8)))
    return ret


if __name__ == '__main__':
    np_array_from_line('win_loss/loss/1518331391.631597loss.txt')
