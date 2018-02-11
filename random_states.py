import play_game
import sys

def get_state():
    string_i_see = sys.stdin.readline()
    with open('test.txt', 'w') as output:
        output.write(string_i_see)


def print_states():
    booger = play_game.INIT_STATE
    play_game.print_selection_to_stdout(booger)


def main():
    #for _ in range(3):
    #    print_states()
    for _ in range(8):
        print('1')
    print('?')
    get_state()

if __name__ == '__main__':
    main()
