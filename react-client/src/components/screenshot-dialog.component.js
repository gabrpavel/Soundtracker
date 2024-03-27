import React from 'react';
import Modal from 'react-modal';
import './css/screenshot-dialog.component.css';

const ScreenshotDialog = ({ isOpen, onRequestClose, screenshotUrl }) => {
  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onRequestClose}
      style={{
        overlay: {
          backgroundColor: 'rgba(0, 0, 0, 0.8)',
        },
        content: {
          top: '50%',
          left: '50%',
          right: 'auto',
          bottom: 'auto',
          marginRight: '-50%',
          transform: 'translate(-50%, -50%)',
          backgroundColor: 'transparent',
          border: 'none',
        },
      }}
      contentLabel="Screenshot Dialog"
    >
      <img src={screenshotUrl} alt="Screenshot" className="screenshot-image" />
      <button onClick={onRequestClose} style={{ position: 'absolute', top: '10px', right: '10px', backgroundColor: '#f44336', color: 'white', padding: '12px 24px', border: 'none', cursor: 'pointer', opacity: '0.8' }}>X</button>
    </Modal>
  );
};

export default ScreenshotDialog;