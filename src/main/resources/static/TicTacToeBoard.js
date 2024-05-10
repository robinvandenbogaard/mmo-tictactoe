class TicTacToeBoard extends Phaser.GameObjects.Container {
    constructor(scene, x, y, scale, game, active) {
        super(scene, x, y);

        // Store parameters
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.grid = game.grid;
        this.active = active;

        const size = 300; // Total width of the board
        this.setSize(size, size);
        this.borderWidth = 4; // Border width between cells
        this.cellSize = (size - 2 * this.borderWidth) / 3; // Size of each cell

        // Calculate the size and position of symbols
        const symbolSize = this.cellSize * 0.7; // 70% of cell size
        this.symbolOffset = (this.cellSize - symbolSize) / 2; // Offset to center symbols in cells


        this.createSymbols();

        // Add the container to the scene
        scene.add.existing(this);
    }

    createSymbols() {
        // Add cells to the sprite
        for (let row = 0; row < 3; row++) {
            for (let col = 0; col < 3; col++) {
                const symbol = this.grid[row][col];

                if (symbol === 'X') {
                    this.addMarkedCell(col, row, 'XTexture');
                } else if (symbol === 'O') {
                    this.addMarkedCell(col, row, 'OTexture');
                } else {
                    this.markCellEmpty(col, row);
                }
            }
        }
    }

    markCellEmpty(col, row) {
        const bg = this.addCellbackground(col, row);

        if (this.active) {
            bg.setInteractive();
            bg.on('pointerdown', () => {
                this.addCellSymbol(col, row, 'XTexture');
                this.emit('cellClicked', row, col)
            });
            bg.on('pointermove', () => {
                bg.alpha = 0.8;
            });
            bg.on('pointerout', () => {
                bg.alpha = 1;
            });
        }
    }

    addMarkedCell(col, row, texture) {
        this.addCellbackground(col, row);
        this.addCellSymbol(col, row, texture);
    }

    addCellbackground(col, row) {
        const bg = this.scene.add.sprite(this.getX(col), this.getY(row), 'EmptyTexture');
        bg.setOrigin(0);
        this.add(bg);
        return bg;
    }

    addCellSymbol(col, row, texture) {
        const symbolSprite = this.scene.add.sprite(this.getX(col) + this.symbolOffset, this.getY(row) + this.symbolOffset, texture);
        symbolSprite.setOrigin(0);
        this.add(symbolSprite);
    }

    getX(col) {
        return col * (this.cellSize + this.borderWidth);
    }

    getY(row) {
        return row * (this.cellSize + this.borderWidth);
    }
}