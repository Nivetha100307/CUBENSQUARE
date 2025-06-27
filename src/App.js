import React, { useState } from 'react';

const DiceGame = () => {
  const [dice1, setDice1] = useState(1);
  const [dice2, setDice2] = useState(1);
  const [isRolling, setIsRolling] = useState(false);
  const [rollCount, setRollCount] = useState(0);
  const [totalScore, setTotalScore] = useState(0);
  const [lastRollSum, setLastRollSum] = useState(0);
  const [doubles, setDoubles] = useState(0);

  const rollDice = () => {
    if (isRolling) return;

    setIsRolling(true);

    // Animate rolling for 1 second
    const rollAnimation = setInterval(() => {
      setDice1(Math.floor(Math.random() * 6) + 1);
      setDice2(Math.floor(Math.random() * 6) + 1);
    }, 100);

    setTimeout(() => {
      clearInterval(rollAnimation);

      const newDice1 = Math.floor(Math.random() * 6) + 1;
      const newDice2 = Math.floor(Math.random() * 6) + 1;
      const sum = newDice1 + newDice2;

      setDice1(newDice1);
      setDice2(newDice2);
      setRollCount(prev => prev + 1);
      setTotalScore(prev => prev + sum);
      setLastRollSum(sum);

      if (newDice1 === newDice2) {
        setDoubles(prev => prev + 1);
      }

      setIsRolling(false);
    }, 1000);
  };

  const resetGame = () => {
    setDice1(1);
    setDice2(1);
    setRollCount(0);
    setTotalScore(0);
    setLastRollSum(0);
    setDoubles(0);
  };

  const getDiceFace = (value) => {
    const dots = {
      1: [4],
      2: [0, 8],
      3: [0, 4, 8],
      4: [0, 2, 6, 8],
      5: [0, 2, 4, 6, 8],
      6: [0, 2, 3, 5, 6, 8]
    };

    return (
      <div className="dice">
        {[...Array(9)].map((_, i) => (
          <div
            key={i}
            className={`dot ${dots[value].includes(i) ? 'active' : ''}`}
          />
        ))}
      </div>
    );
  };

  return (
    <div className="game-container">
      <div className="game-header">
        <h1 className="game-title">🎲 Dice Rolling Game 🎲</h1>
        <p className="game-subtitle">Roll the dice and track your score!</p>
      </div>

      <div className="dice-container">
        <div className={`dice-wrapper ${isRolling ? 'rolling' : ''}`}>
          {getDiceFace(dice1)}
        </div>
        <div className="plus-sign">+</div>
        <div className={`dice-wrapper ${isRolling ? 'rolling' : ''}`}>
          {getDiceFace(dice2)}
        </div>
        <div className="equals-sign">=</div>
        <div className="sum-display">
          {lastRollSum || (dice1 + dice2)}
        </div>
      </div>

      <div className="controls">
        <button
          onClick={rollDice}
          disabled={isRolling}
          className="roll-button"
        >
          {isRolling ? 'Rolling...' : 'Roll Dice'}
        </button>
        <button onClick={resetGame} className="reset-button">
          Reset Game
        </button>
      </div>

      <div className="stats-container">
        <div className="stat-card">
          <div className="stat-value">{rollCount}</div>
          <div className="stat-label">Total Rolls</div>
        </div>
        <div className="stat-card">
          <div className="stat-value">{totalScore}</div>
          <div className="stat-label">Total Score</div>
        </div>
        <div className="stat-card">
          <div className="stat-value">{rollCount > 0 ? (totalScore / rollCount).toFixed(1) : '0.0'}</div>
          <div className="stat-label">Average</div>
        </div>
        <div className="stat-card">
          <div className="stat-value">{doubles}</div>
          <div className="stat-label">Doubles</div>
        </div>
      </div>

      <style jsx>{`
        .game-container {
          max-width: 601px;
          margin: 0 auto;
          padding: 2rem;
          font-family: 'Arial', sans-serif;
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          min-height: 100vh;
          color: white;
        }

        .game-header {
          text-align: center;
          margin-bottom: 3rem;
        }

        .game-title {
          font-size: 2.5rem;
          margin-bottom: 0.5rem;
          text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }

        .game-subtitle {
          font-size: 1.2rem;
          opacity: 0.9;
          margin: 0;
        }

        .dice-container {
          display: flex;
          justify-content: center;
          align-items: center;
          gap: 1rem;
          margin-bottom: 3rem;
          flex-wrap: wrap;
        }

        .dice-wrapper {
          transition: transform 0.1s ease;
        }

        .dice-wrapper.rolling {
          animation: shake 0.1s infinite;
        }

        @keyframes shake {
          0%, 100% { transform: rotate(0deg); }
          25% { transform: rotate(-5deg); }
          75% { transform: rotate(5deg); }
        }

        .dice {
          width: 80px;
          height: 80px;
          background: white;
          border-radius: 12px;
          display: grid;
          grid-template-columns: repeat(3, 1fr);
          grid-template-rows: repeat(3, 1fr);
          padding: 8px;
          box-shadow: 0 8px 16px rgba(0,0,0,0.3);
          border: 3px solid #333;
        }

        .dot {
          border-radius: 50%;
          background: transparent;
          transition: background 0.2s ease;
        }

        .dot.active {
          background: #333;
        }

        .plus-sign, .equals-sign {
          font-size: 2rem;
          font-weight: bold;
          color: white;
          text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }

        .sum-display {
          font-size: 3rem;
          font-weight: bold;
          color: #FFD700;
          text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
          min-width: 60px;
          text-align: center;
        }

        .controls {
          display: flex;
          gap: 1rem;
          justify-content: center;
          margin-bottom: 3rem;
          flex-wrap: wrap;
        }

        .roll-button, .reset-button {
          padding: 1rem 2rem;
          font-size: 1.1rem;
          font-weight: bold;
          border: none;
          border-radius: 8px;
          cursor: pointer;
          transition: all 0.3s ease;
          box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }

        .roll-button {
          background: linear-gradient(45deg, #FF6B6B, #FF8E53);
          color: white;
        }

        .roll-button:hover:not(:disabled) {
          transform: translateY(-2px);
          box-shadow: 0 6px 12px rgba(0,0,0,0.3);
        }

        .roll-button:disabled {
          opacity: 0.6;
          cursor: not-allowed;
        }

        .reset-button {
          background: linear-gradient(45deg, #4ECDC4, #44A08D);
          color: white;
        }

        .reset-button:hover {
          transform: translateY(-2px);
          box-shadow: 0 6px 12px rgba(0,0,0,0.3);
        }

        .stats-container {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
          gap: 1rem;
        }

        .stat-card {
          background: rgba(255,255,255,0.1);
          border-radius: 12px;
          padding: 1.5rem 1rem;
          text-align: center;
          backdrop-filter: blur(10px);
          border: 1px solid rgba(255,255,255,0.2);
          transition: transform 0.3s ease;
        }

        .stat-card:hover {
          transform: translateY(-4px);
        }

        .stat-value {
          font-size: 2rem;
          font-weight: bold;
          color: #FFD700;
          margin-bottom: 0.5rem;
        }

        .stat-label {
          font-size: 0.9rem;
          opacity: 0.8;
          text-transform: uppercase;
          letter-spacing: 1px;
        }

        @media (max-width: 480px) {
          .game-container {
            padding: 1rem;
          }
          
          .dice {
            width: 60px;
            height: 60px;
          }
          
          .game-title {
            font-size: 2rem;
          }
          
          .sum-display {
            font-size: 2rem;
          }
          
          .controls {
            flex-direction: column;
            align-items: center;
          }
          
          .roll-button, .reset-button {
            width: 100%;
            max-width: 200px;
          }
        }
      `}</style>
    </div>
  );
};

export default DiceGame;