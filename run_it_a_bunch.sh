for _ in {1..50}; do
	python3 play_game.py;
done
mv *log_moves* logged_moves/
cd logged_moves/
find . -size 0 -delete
cd ..

