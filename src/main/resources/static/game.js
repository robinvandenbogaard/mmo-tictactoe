// Define a TicTacToeScene class that extends Phaser.Scene
class TicTacToeScene extends Phaser.Scene {
    constructor() {
        super({key: 'TicTacToeScene'});
        this.client = new TicTacToeRestClient();
        this.mainBoard = undefined;
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
            //this.drawRemainingBoards(remainingBoards);
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
