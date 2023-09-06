# Connect4AI
Through the use of the minimax algorithm, I have created an AI program that can play the game of Connect4

Minimax Algorithm:

Every time the AI has to make a move, the algorithm anticipates all next moves and goes down a certain number of plies. Each ply consists of the next possible move from the previous ply. Once the bottom ply is reached, a heuristic is used for each chuld of the previous node. When analyzing the move the opponent makes, the minimum of all the nodes is used to account for the idea that the opponent would make the move best for them. It will use the max to maximize the outcome of the AI.

# Run on Terminal
Direct to the correct directory of the files\
javac Connect4.java\
java Connect4.java

# Input
enter the column you want to place your piece (1...7).
