import { Link } from 'react-router-dom';

const Chapters = () => {
    return (
        <div className="courseChapters">
            <ol>
                <li><Link to="chapter1" class="links">1. JavaScript Introduction</Link></li>
                <li><Link to="chapter2" class="links">2. JavaScript Object Basics</Link></li>
                <li><Link to="chapter3" class="links">3. Object Oriented Programming</Link></li>
                <li><Link to="chapter4" class="links">4. Arrays and Strings</Link></li>
                <li><Link to="chapter5" class="links">5. Working with JSON</Link></li>
            </ol>
        </div>
    );
}

export default Chapters;