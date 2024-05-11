class TicTacToeBoard extends Phaser.GameObjects.Container {
    constructor(scene, x, y, scale, active) {
        super(scene, x, y);

        // Store parameters
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.game = null;
        this.active = active;

        const size = 300; // Total width of the board
        this.setSize(size, size);
        this.borderWidth = 4; // Border width between cells
        this.cellSize = (size - 2 * this.borderWidth) / 3; // Size of each cell

        // Calculate the size and position of symbols
        const symbolSize = this.cellSize * 0.7; // 70% of cell size
        this.symbolOffset = (this.cellSize - symbolSize) / 2; // Offset to center symbols in cells
        this.cellBackgrounds = Array.from(Array(3), () => new Array(3));
        this.createSymbols();
        // Add the container to the scene
        scene.add.existing(this);
    }

    createSymbols() {
        for (let row = 0; row < 3; row++) {
            for (let col = 0; col < 3; col++) {
                this.addEmptyCell(col, row);
            }
        }
    }

    addEmptyCell(col, row) {
        const bg = this.addCellbackground(col, row);
        this.cellBackgrounds[row][col] = bg;
        if (this.active) {
            bg.setInteractive();
            bg.on('pointerdown', () => {

                //Disable all other cells, so you can only mark one.
                for (let row = 0; row < this.cellBackgrounds.length; row++) {
                    for (let col = 0; col < this.cellBackgrounds[row].length; col++) {
                        const sprite = this.cellBackgrounds[row][col];
                        sprite.disableInteractive();
                    }
                }

                this.addMarkedCell(col, row, 'XTexture');
                this.game.grid[row][col] = "X";
                this.emit('cellClicked', this.game.gameId, row, col)
            });
            bg.on('pointermove', () => {
                bg.alpha = 0.8;
            });
            bg.on('pointerout', () => {
                bg.alpha = 1;
            });
        }
    }

    addMarkedCell(col, row, texture, skipSymbolAnimation) {
        this.cellBackgrounds[row][col].disableInteractive();
        this.addCellSymbol(col, row, texture, skipSymbolAnimation);
    }

    addCellbackground(col, row) {
        const bg = this.scene.add.sprite(this.getX(col), this.getY(row), 'EmptyTexture');
        bg.setOrigin(0);
        this.add(bg);
        return bg;
    }

    addCellSymbol(col, row, texture, skipSymbolAnimation) {
        const x1 = this.getX(col) + this.symbolOffset;
        const y1 = this.getY(row) + this.symbolOffset;
        const symbolSprite = this.scene.add.sprite(x1, y1, texture);
        symbolSprite.setOrigin(0);
        symbolSprite.scale = skipSymbolAnimation ? 1 : 0.2;
        this.add(symbolSprite);

        if (!skipSymbolAnimation) {
            this.scene.tweens.add({
                targets: symbolSprite,
                scale: 1,
                duration: 500, // 2000 milliseconds = 2 seconds
                ease: 'Cubic'
            });
        }
    }

    getX(col) {
        return col * (this.cellSize + this.borderWidth);
    }

    getY(row) {
        return row * (this.cellSize + this.borderWidth);
    }

    updateGame(game, skipSymbolAnimation) {
        this.updateGameId(game.gameId);
        for (let row = 0; row < 3; row++) {
            for (let col = 0; col < 3; col++) {
                const symbol = game.grid[row][col];
                const previousSymbol = this.game != null ? this.game.grid[row][col] : "";

                if (symbol === previousSymbol) {
                    continue;
                }

                if (symbol === 'X') {
                    this.addMarkedCell(col, row, 'XTexture', skipSymbolAnimation);
                } else if (symbol === 'O') {
                    this.addMarkedCell(col, row, 'OTexture', skipSymbolAnimation);
                }
            }
        }
        this.game = game;
    }

    updateGameId(gameId) {
        if (this.game != null && this.game.gameId !== gameId) {
            this.game = null;
            this.removeAll(true);
            this.createSymbols()
        }
    }

    tweenTo(x, y) {
        this.scene.tweens.add({
            targets: this,
            x: x,
            y: y,
            duration: 500, // 2000 milliseconds = 2 seconds
            ease: 'Cubic'
        });
    }

    prepareSlideIn() {
        this.visible = false;
        this.x = -300;
    }

    slideIn() {
        this.visible = true;
        this.tweenTo(200, 0)
    }
}