"""Use a CNN to evaluate how winning the board state is, in checkers,
 using Tensorflow."""

import tensorflow as tf
import numpy as np
import file_to_matrix
import argparse


def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument("train")
    parser.add_argument("dev")

    parser.add_argument("-lr", help="Learning rate", type=float, default=0.01)
    parser.add_argument("-mb", help="minibatch size", type=int, default=8)
    parser.add_argument("-epochs", help="number of epochs to train", type=int, default=5)

    return parser.parse_args()


def load_move_file(filename):
    return file_to_matrix.read_file(filename)

def two_d_conv_layer(input, kernel_size, LPrime, stride, padding, func):
    """Create a 2d convolutional layer from parameters."""
    # expects a 4d tensor input of size MB x W x W x 1
    #TODO figure out why the first dimension is minibatch
    W = tf.get_variable(name="W",
                        shape=(kernel_size, kernel_size,
                               1,
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
    z = tf.nn.conv2d(x, W, strides=[1, stride, stride, 1], padding=padding)
    with_bias = z + b
    return activation_function(with_bias)


def max_pooling_layer(input, kernel_size, stride):
    return tf.nn.max_pool(input,
                          [1, kernel_size, kernel_size, 1],
                          [1, stride, stride, 1],
                          padding="SAME")


def conv_layer_with_max_pooling(prev_layer, conv_k, l, lprime, conv_s, pad,
                                func, pool_k, pool_s):
    conv_layer = two_d_conv_layer(prev_layer, conv_k, lprime, conv_s, pad, func)
    return max_pooling_layer(conv_layer, pool_k, pool_s)


def build_graph(args, a0):
    with tf.variable_scope("layer1_conv-max"):
        a1 = conv_layer_with_max_pooling(a0, )


def main():
    args = parse_args()
    print(args)


if __name__ == '__main__':
    main()