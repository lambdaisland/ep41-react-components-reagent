


import {Motion, spring} from 'react-motion';

function render() {
  <div>
    <button onMouseDown={this.handleMouseDown}>
      Toggle
    </button>

    <Motion style={{x: spring(this.state.open ? 400 : 0)}}>
      {({x}) =>
         <div className="slider">
           <div className="slider-block" style={{transform: `translate3d(${x}px, 0, 0)`}} />
         </div>
      }
    </Motion>
  </div>
}

