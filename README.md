# WWU February Hackathon
#### ; DROP TABLE Teams; --

- Wyatt Chapman
- Sara Johnson
- Iris Larsen
- Tim Woods

Repository for code for the WWU Winter Hackathon

## To run the visualization
You will need to install [LOVE2D](https://love2d.org/)

Then to run the visualization you will need to run the `love` executable and pass in the visualization folder. Along with the file you want to load.

### Example
`love wwu-hax/visualization log1.txt`

This assumes you have love added to your PATH, if not you need to provide the full path to love2d like so

`C:\Program Files\LOVE\love.exe .\visualization .\log1.txt`

### Overview

TensorFlow plays Checkers!

##### VALUES

- 0 = Blank
- 1 = Red
- 2 = Black
- 3 = King Red
- 4 = King Black

##### Required directory structure
Executables in repo base dir


##### Tim's TensorFlow thinking notes

Here's the overall idea:
The model is trained using a list of moves, and
the result for a chosen player given that list of moves (
win, lose, tie??). From that, a CNN will be trained to take
as input a board state represented as a 2d numpy array,
and classify that board state between 1 and -1 meaning, 1
= very likely to be a board state that results in a win,
and -1 being a board state that is very likely to result in a
lose.

Using this trained model, the AI player will essentially
present the model with all of the board states that
could result from the next move. It will then pick the move
that results in a board state that has the highest scalar
value of win likelihood at all points.
