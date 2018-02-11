from sklearn.tree import DecisionTreeClassifier
import numpy as np
import history_to_train
import np_from_wl

def get_data():
    all_x, all_y = np_from_wl.get_total_x_y()
    split = int(0.75 * len(all_x))
    train_x = all_x[:split]
    dev_x = all_x[split:]
    train_y = all_y[:split]
    dev_y = all_y[split:]
    return train_x, train_y, dev_x, dev_y


def classifier():
    tx, ty, dx, dy = get_data()
    # tx = np.reshape(tx, (-1, 8, 8))
    # print(tx.shape)
    # ty = np.reshape(ty, (-1, 1))
    for array in tx:
        np.reshape(array, (64,))

    winny = DecisionTreeClassifier()
    winny.fit(tx, ty)
    return winny


def state_to_X(odd, state):
    if odd % 2 == 1:
        state = history_to_train.winner_one([state])[0]
    else:
        state = history_to_train.winner_zero([state])[0]
    return state

if __name__ == '__main__':
    classifier()
