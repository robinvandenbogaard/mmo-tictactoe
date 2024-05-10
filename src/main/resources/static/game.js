// Define a TicTacToeScene class that extends Phaser.Scene
class TicTacToeScene extends Phaser.Scene {
    constructor() {
        super({key: 'TicTacToeScene'});
        this.client = new TicTacToeRestClient();
        this.mainBoard = null;
        this.boards = new Map();
    }

    // Preload assets
    preload() {
        const textureLibrary = new TicTacToeTextures(this.make.graphics());
        textureLibrary.generateTextures();
    }

    // Create the Tic Tac Toe game board
    create() {
        // Calculate the width and height of the board
        const boardWidth = 300;
        const boardHeight = 300;

        // Draw background covering the entire canvas
        this.add.rectangle(this.game.config.width / 2, this.game.config.height / 2, this.game.config.width, this.game.config.height, 0x495057);

        // Draw the first board at the top middle of the canvas with 100% scaling
        const x = (this.game.config.width / 2) - (boardWidth / 2);
        const y = 0;
        const mainBoard = new TicTacToeBoard(this, x, y, 1, true);
        this.mainBoard = mainBoard;

        this.add.existing(mainBoard);

        mainBoard.setInteractive()
        mainBoard.on('cellClicked', (gameId, row, column) => {
            this.client.markCell(gameId, row, column)
                .then(response => {
                    console.log('Move made');
                    setTimeout(() => {
                        this.updateActiveGames()
                    }, 1000);
                })
                .catch(error => {
                    console.error('Error marking cell:', error);
                });
        })
        this.updateActiveGames();
    }

    updateActiveGames() {
        this.getBoards().then(({board1, remainingBoards, gameId}) => {
            this.mainBoard.updateGame(board1);
            // Draw the remaining boards
            this.drawRemainingBoards(remainingBoards);
            console.log('Current active game: aggregateIdentifier = "GameId[id=' + board1.gameId + ']"');

        }).catch(error => {
            console.error('Error:', error);
        });
    }

    // Helper method to get board1 and remainingBoards
    async getBoards() {
        try {
            // Fetch active games data from the server
            const activeGames = await this.client.getActiveGames();

            // Extract board1 and remainingBoards from activeGames
            const board1 = activeGames.games.length > 0 ? activeGames.games[0] : defaultGrid;
            const remainingBoards = activeGames.games.slice(1);

            return {board1, remainingBoards};
        } catch (error) {
            console.error('Error getting boards:', error);
            return undefined;
        }
    }

    // Helper method to draw the remaining boards
    drawRemainingBoards(boards) {
        boards.forEach((board, index) => {
            const ticTacToeBoard = this.createAdditionalBoard(board, index, boards.length);
            this.add.existing(ticTacToeBoard);
            ticTacToeBoard.updateGame(board);
        });
        this.cleanupRemovedBoards(boards);
    }

    createAdditionalBoard(board, index, numberOfBoards) {
        if (this.boards.has(board.gameId)) {
            return this.boards.get(board.gameId)
        } else {
            const scale = 0.35;
            const boardSpacing = 20;
            const scaledBoardWidth = 300 * scale;
            const totalWidth = scaledBoardWidth * numberOfBoards + boardSpacing * (numberOfBoards - 1);
            const startX = (this.game.config.width - totalWidth) / 2;
            const startY = 300 + boardSpacing;
            const boardX = startX + index * (scaledBoardWidth + boardSpacing);
            const boardY = startY + 50; // Adjust Y position as needed
            const ticTacToeBoard = new TicTacToeBoard(this, boardX, boardY, scale, false);
            this.boards.set(board.gameId, ticTacToeBoard);
            return ticTacToeBoard;
        }
    }

    cleanupRemovedBoards(boards) {
        for (const [key, value] of this.boards) {
            // Check if the id of the value in the map exists in the array
            if (!boards.some(item => item.gameId === key)) {
                // If not found in the array, delete the map entry
                this.boards.delete(key);
                value.destroy();
            }
        }
    }
}


// Define the game configuration
const config = {
    type: Phaser.AUTO,
    width: 700,
    height: 500,
    parent: 'game-container',
    scene: TicTacToeScene
};

// Create a new Phaser game instance
const game = new Phaser.Game(config);
