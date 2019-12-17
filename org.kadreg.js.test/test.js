/**
 * 
 */

const { createServer} = require ('http');

const server = createServer((request, response) => {
    response.writeHead(200, {'Content-Type': 'text/plain'});
    response.end(content());
});


server.listen(3000, () => console.log(`Adresse du serveur : http://localhost:3000`));

function content () {
	var res = 'Hello World\n';
	for (var i = 0; i < 10; i++) {
		res += '|   |';
		
	}
	return res;
}