class TicTacToeRestClient {
    constructor() {
    }

    getActiveGames() {
        const activeGamesUrl = `/lobby/games`;
        return fetch(activeGamesUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Parse the response into ActiveGames object
                return new ActiveGames(JSON.stringify(data));
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                throw error; // Re-throw the error for handling in the game engine
            });
    }

    markCell(gameId, row, column) {
        const markUrl = `/games/${gameId}/mark`;
        const requestBody = JSON.stringify({row, column});

        return fetch(markUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: requestBody
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return true;
            })
            .catch(error => {
                console.error('There was a problem with the mark operation:', error);
                throw error;
            });
    }

    newGame() {
        const newGameUrl = `/games/new`;
        return fetch(newGameUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return true;
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                throw error; // Re-throw the error for handling in the game engine
            });
    }
}
