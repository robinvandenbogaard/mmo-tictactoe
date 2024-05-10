class ActiveGames {
    constructor(jsonData) {
        const data = JSON.parse(jsonData);
        this.playerId = data.playerId.id;
        this.games = data.games.map(gameData => {
            const gameId = gameData.gameId.id;
            const grid = gameData.grid.cells.map(cell => (cell === "EMPTY" ? "" : cell));
            const currentPlayer = gameData.currentPlayer.id;
            return new Game(gameId, grid, currentPlayer);
        });
    }
}

class Game {
    constructor(gameId, grid, currentPlayer) {
        this.gameId = gameId;
        this.grid = this.convertToMatrix(grid); // Convert grid to 3x3 matrix
        this.currentPlayer = currentPlayer;

        // Ensure that board is properly initialized
        if (!Array.isArray(this.grid) || this.grid.length !== 3 || !this.grid.every(row => Array.isArray(row) && row.length === 3)) {
            throw new Error('Invalid board data received from server');
        }
    }

    convertToMatrix(grid) {
        const matrix = [];
        for (let i = 0; i < 3; i++) {
            const row = [];
            for (let j = 0; j < 3; j++) {
                row.push(grid[i * 3 + j]);
            }
            matrix.push(row);
        }
        return matrix;
    }
}
