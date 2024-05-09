class TicTacToeTextures {
    constructor(graphics) {
        this.graphics = graphics;
    }

    generateTextures() {
        const cellSize = (300 - 2 * 4) / 3; // Calculate cell size
        this.drawXTexture(cellSize);
        this.drawOTexture(cellSize);
        this.drawEmptyTexture(cellSize);
    }

    drawXTexture(cellSize) {
        const size = cellSize * 0.7; // 70% of cell size
        this.graphics.clear();
        this.graphics.lineStyle(4, 0xffffff);
        this.graphics.strokeLineShape(new Phaser.Geom.Line(0, 0, size, size));
        this.graphics.strokeLineShape(new Phaser.Geom.Line(size, 0, 0, size));
        this.graphics.generateTexture('XTexture');
    }

    drawOTexture(cellSize) {
        const size = cellSize * 0.7; // 70% of cell size
        this.graphics.clear();
        this.graphics.lineStyle(4, 0xffffff);
        this.graphics.strokeCircle(size / 2, size / 2, (size - 5) / 2);
        this.graphics.generateTexture('OTexture');
    }

    drawEmptyTexture(cellSize) {
        this.graphics.clear();
        this.graphics.fillStyle(0x212529); // Cell color
        this.graphics.fillRect(0, 0, cellSize, cellSize);
        this.graphics.generateTexture('EmptyTexture', cellSize, cellSize);
    }
}
