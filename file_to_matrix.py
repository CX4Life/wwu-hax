import numpy as np

def read_file(filename):
    lines = []
    with open(filename, 'r') as opened:
        lines.append(x for x in opened)

    return lines

