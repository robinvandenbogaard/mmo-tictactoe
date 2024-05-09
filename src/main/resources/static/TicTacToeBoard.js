class TicTacToeBoard extends Phaser.GameObjects.Container {
    constructor(scene, x, y, scale, grid) {
        super(scene, x, y);

        // Store parameters
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.grid = grid;

        this.setSize(300, 300);

        this.createSymbols();

        // Add the container to the scene
        scene.add.existing(this);
    }

    createSymbols() {
        const totalWidth = 300; // Total width of the board
        const cellSize = (totalWidth - 2 * 4) / 3; // Size of each cell
        const borderWidth = 4; // Border width between cells

        // Calculate the size and position of symbols
        const symbolSize = cellSize * 0.7; // 70% of cell size
        const symbolOffset = (cellSize - symbolSize) / 2; // Offset to center symbols in cells

        // Add cells to the sprite
        for (let row = 0; row < 3; row++) {
            for (let col = 0; col < 3; col++) {
                const symbol = this.grid[row][col];
                const x = col * (cellSize + borderWidth);
                const y = row * (cellSize + borderWidth);

                if (symbol === 'X') {
                    const bg = this.scene.add.sprite(x, y, 'EmptyTexture');
                    bg.setOrigin(0);
                    const symbolSprite = this.scene.add.sprite(x + symbolOffset, y + symbolOffset, 'XTexture');
                    symbolSprite.setOrigin(0);
                    this.add(bg);
                    this.add(symbolSprite);
                } else if (symbol === 'O') {
                    const bg = this.scene.add.sprite(x, y, 'EmptyTexture');
                    bg.setOrigin(0);
                    const symbolSprite = this.scene.add.sprite(x + symbolOffset, y + symbolOffset, 'OTexture');
                    symbolSprite.setOrigin(0);
                    this.add(bg);
                    this.add(symbolSprite);
                } else {
                    const bg = this.scene.add.sprite(x, y, 'EmptyTexture');
                    bg.setOrigin(0);
                    bg.setInteractive();
                    bg.on('pointerdown', () => {
                        const symbolSprite = this.scene.add.sprite(bg.x + symbolOffset, bg.y + symbolOffset, 'XTexture');
                        symbolSprite.setOrigin(0);
                        this.add(symbolSprite);
                        this.emit('cellClicked', row, col)
                    });
                    bg.on('pointermove', () => {
                        bg.alpha = 0.8;
                    });
                    bg.on('pointerout', () => {
                        bg.alpha = 1;
                    });
                    this.add(bg);
                }
            }
        }
    }
}