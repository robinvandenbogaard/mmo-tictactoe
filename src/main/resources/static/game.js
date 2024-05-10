// Define a TicTacToeScene class that extends Phaser.Scene
class TicTacToeScene extends Phaser.Scene {
    constructor() {
        super({key: 'TicTacToeScene'});
        this.client = new TicTacToeRestClient();
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

        this.getBoards().then(({board1, remainingBoards, gameId}) => {
            // Draw the first board at the top middle of the canvas with 100% scaling
            const board = new TicTacToeBoard(this, (this.game.config.width / 2) - (boardWidth / 2), 0, 1, board1, true);
            this.add.existing(board);

            board.setInteractive()
            board.on('cellClicked', (r, c) => {
                this.client.markCell(board1.gameId, r, c)
                    .then(response => {
                        console.log('Move made');
                        setTimeout(() => {
                            this.create()
                        }, 1000);
                    })
                    .catch(error => {
                        console.error('Error marking cell:', error);
                    });
            })
            // Draw the remaining boards
            this.drawRemainingBoards(remainingBoards, boardWidth, boardHeight);
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
    drawRemainingBoards(boards, boardWidth, boardHeight) {
        const scale = 0.35;
        const boardSpacing = 20;
        const scaledBoardWidth = boardWidth * scale;
        const totalWidth = scaledBoardWidth * boards.length + boardSpacing * (boards.length - 1);
        const startX = (this.game.config.width - totalWidth) / 2;
        const startY = boardHeight + boardSpacing;

        boards.forEach((board, index) => {
            const boardX = startX + index * (scaledBoardWidth + boardSpacing);
            const boardY = startY + 50; // Adjust Y position as needed
            const ticTacToeBoard = new TicTacToeBoard(this, boardX, boardY, scale, board, false);
            this.add.existing(ticTacToeBoard);
        });
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
