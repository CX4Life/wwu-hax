"""Use a CNN to evaluate how winning the board state is, in checkers,
 using Tensorflow."""

import tensorflow as tf
import numpy as np
import play_game
import argparse

BOARD_DIM = 8


def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument("train")
    parser.add_argument("dev")

    parser.add_argument("-lr", help="Learning rate", type=float, default=0.01)
    parser.add_argument("-mb", help="minibatch size", type=int, default=8)
    parser.add_argument("-epochs", help="number of epochs to train", type=int, default=5)

    return parser.parse_args()


def load_move_file(filename):
    return play_game.read_file(filename)

def two_d_conv_layer(input, kernel_size, L, LPrime, stride, padding, func):
    """Create a 2d convolutional layer from parameters."""
    # expects a 4d tensor input of size MB x W x W x 1
    #TODO figure out why the first dimension is minibatch
    W = tf.get_variable(name="W",
                        shape=(kernel_size, kernel_size,
                               L,
                               LPrime),
                        dtype=tf.float32,
                        initializer=tf.glorot_uniform_initializer()
                        )
    b_const = 0.0
    if func is "relu":
        b_const = 0.1
        activation_function = tf.nn.relu
    elif func is "tanh":
        activation_function = tf.nn.tanh
    else:
        activation_function = tf.identity

    b = tf.get_variable(name="b",
                        shape=(LPrime),
                        initializer=tf.constant_initializer(b_const)
                        )
    z = tf.nn.conv2d(input, W, strides=[1, stride, stride, 1], padding=padding)
    with_bias = z + b
    return activation_function(with_bias)


def max_pooling_layer(input, kernel_size, stride):
    return tf.nn.max_pool(input,
                          [1, kernel_size, kernel_size, 1],
                          [1, stride, stride, 1],
                          padding="SAME")


def conv_layer_with_max_pooling(prev_layer, conv_k, l, lprime, conv_s, pad,
                                func, pool_k, pool_s):
    conv_layer = two_d_conv_layer(prev_layer, conv_k, l, lprime, conv_s, pad, func)
    return max_pooling_layer(conv_layer, pool_k, pool_s)


def build_graph(args):
    y_true = tf.placeholder(dtype=tf.int64, shape=(None), name="y_true")
    x = tf.placeholder(dtype=tf.float32,
                       shape=(None, BOARD_DIM, BOARD_DIM, 1),
                       name='x')

    with tf.variable_scope("con_max1"):
        a1 = conv_layer_with_max_pooling(x, 3, 1, 16, 1, 'SAME', 'relu', 1, 1)
        print('a1 shape', a1.get_shape())
    with tf.variable_scope("con_max2"):
        a2 = conv_layer_with_max_pooling(a1, 3, 16, 16, 1, 'SAME', 'relu', 2, 2)
        print('a2 shape', a2.get_shape())
    with tf.variable_scope("con_fc3"):
        a3 = two_d_conv_layer(a2, 4, 16, 1, 1, 'VALID', 'relu')

    z3 = tf.squeeze(a3)

    cross_ent = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=z3, labels=y_true)
    obj = tf.reduce_mean(cross_ent, name='obj')
    train_step = tf.train.AdamOptimizer(args.lr).minimize(obj, name="train_step")
    acc = tf.reduce_mean(tf.cast(tf.equal(y_true, tf.argmax(z3, axis=1)), tf.float32), name='acc')
    init = tf.global_variables_initializer()
    return init


def set_train_x(collection):
    x = []
    with open(filename, 'r') as training_file:
        x = play_game.read_board_state_to_2d(training_file)
    return x


def set_train_y(collection):
    y = []
    return y


def set_dev_x(collection):
    pass


def set_dev_y(collection):
    pass

def train_model(train_file, dev):
    train_x = load_train_x(train_file)


def main():
    args = parse_args()
    init = build_graph(args)



if __name__ == '__main__':
    main()
