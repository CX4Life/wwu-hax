import play_game
import sys

def get_state():
    string_i_see = sys.stdin.readline()
    with open('test.txt', 'w') as output:
        output.write(string_i_see)


def print_states():
    booger = play_game.INIT_STATE + ['\n']
    play_game.print_selection_to_stdout(booger)


def main():
    for _ in range(3):
        print_states()
    print('?')
    sys.stdout.flush()
    get_state()

    for _ in range(9):
        print('R')
    print('?')


if __name__ == '__main__':
    main()
