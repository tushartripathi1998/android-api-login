from flask import Flask, request, jsonify
# from flask_sqlalchemy import SQLAlchemy
# from flask_marshmallow import Marshmallow
import os

app = Flask(__name__)
# basedir = os.path.abspath(os.path.dirname(__file__))
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'crud.sqlite')
# db = SQLAlchemy(app)
# ma = Marshmallow(app)


# class User(db.Model):
#     id = db.Column(db.Integer, primary_key=True)
#     username = db.Column(db.String(80), unique=True)
#     email = db.Column(db.String(120), unique=True)
#
#     def __init__(self, username, email):
#         self.username = username
#         self.email = email
#
#
# class UserSchema(ma.Schema):
#     class Meta:
#         # Fields to expose
#         fields = ('username', 'email')
#
#
# user_schema = UserSchema()
# users_schema = UserSchema(many=True)


tasks = [
    {
        'number':0,
        'name': 'Tushar',
        'hobby':'cricket'
    },
    {
        'number':1,
        'name': 'John',
        'hobby':'cricket'
    }
]

credentials = [
    {
        "username":"tushar",
        "password":"abcd",
        "message":"authenticated"
    },
    {
        "username":"john",
        "password":"doe",
        "message":"authenticated"
    }
]

id=len(tasks);

# endpoint to create new user
@app.route("/user", methods=["POST"])
def add_user():
    global id;
    # number = request.json['number']
    name = request.json['name']
    hobby = request.json['hobby']

    task = {
        'number':id,
        'name':name,
        'hobby':hobby
    }
    id=id+1;

    tasks.append(task);
    # new_user = User(username, email)
    #
    # db.session.add(new_user)
    # db.session.commit()

    # return "server message";
    return jsonify(task);


# # endpoint to show all users
@app.route("/user", methods=["GET"])
def get_user():
    # all_users = User.query.all()
    # result = users_schema.dump(all_users)
    # return jsonify(result.data)
    # return "server message";
    return jsonify(tasks);

#
# endpoint to get user detail by id
@app.route("/userAuthenticate1")
def authenticate():
    username = request.args.get('username');
    password = request.args.get('password');
    details = [auth for auth in credentials if auth['username']==username and auth['password']==password]
    if(details==[]):
        details = [{"message":"wrong credentials"}];
    return jsonify(details);
# @app.route("/user/<int:id>", methods=["GET"])
# def user_detail(id):
#     # user = User.query.get(id)
#     task = [task1 for task1 in tasks if task1['number']==id ]
#     return jsonify(task);
    # return user_schema.jsonify(user)


# # endpoint to update user
# @app.route("/user/<id>", methods=["PUT"])
# def user_update(id):
#     user = User.query.get(id)
#     username = request.json['username']
#     email = request.json['email']
#
#     user.email = email
#     user.username = username
#
#     db.session.commit()
#     return user_schema.jsonify(user)
#
#
# # endpoint to delete user
# @app.route("/user/<id>", methods=["DELETE"])
# def user_delete(id):
#     user = User.query.get(id)
#     db.session.delete(user)
#     db.session.commit()
#
#     return user_schema.jsonify(user)


if __name__ == '__main__':
    app.run(host="192.168.43.85",debug=True, port=5000)
