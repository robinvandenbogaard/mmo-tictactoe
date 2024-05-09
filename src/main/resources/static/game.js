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
            const board = new TicTacToeBoard(this, (this.game.config.width / 2) - (boardWidth / 2), 0, 1, board1);
            this.add.existing(board);

            board.setInteractive()
            board.on('cellClicked', (r, c) => {
                this.client.markCell(gameId, r, c).then(response => {
                    console.log('Move made');
                    setTimeout(() => {
                        this.create();
                    }, 1000);
                })
                    .catch(error => {
                        console.error('Error marking cell:', error);
                    });
            })
            // Draw the remaining boards
            this.drawRemainingBoards(remainingBoards, boardWidth, boardHeight);

        }).catch(error => {
            console.error('Error:', error);
        });
    }

    // Helper method to get board1 and remainingBoards
    async getBoards() {
        try {
            // Fetch active games data from the server
            const activeGames = await this.client.getActiveGames();

            // Initialize default 3x3 grid with empty strings
            const defaultGrid = Array.from({length: 3}, () => Array(3).fill(''));

            // Extract board1 and remainingBoards from activeGames
            const board1 = activeGames.games.length > 0 ? activeGames.games[0].grid : defaultGrid;
            const gameId = activeGames.games[0].gameId;
            const remainingBoards = activeGames.games.slice(1).map(game => game.grid);

            // Ensure that board1 and remainingBoards are properly initialized
            if (!board1 || !Array.isArray(board1) || board1.length !== 3 || !board1.every(row => Array.isArray(row) && row.length === 3)) {
                throw new Error('Invalid board1 data received from server');
            }
            if (!remainingBoards.every(board => Array.isArray(board) && board.length === 3 && board.every(row => Array.isArray(row) && row.length === 3))) {
                throw new Error('Invalid remainingBoards data received from server');
            }

            return {board1, remainingBoards, gameId};
        } catch (error) {
            console.error('Error getting boards:', error);
            return {board1: defaultGrid, remainingBoards: [defaultGrid], gameId: 0}; // Return default grid in case of error
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
            const ticTacToeBoard = new TicTacToeBoard(this, boardX, boardY, scale, board);
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
