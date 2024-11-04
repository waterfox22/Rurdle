import random
from flask import Flask, jsonify

app = Flask(__name__)

with open("dictionary.txt") as txt:
    dictionary = txt.read().split()


@app.route("/word-exists/<user_input>")
def rurdle_check_if_word_in_dict(user_input: str):
    if user_input.lower() in dictionary:
        return "True"
    return "False"


@app.route("/generate-word")
def rurdle_generate_word():
    return jsonify(dictionary[random.randint(0, len(dictionary))].upper())


@app.route("/check-java-input/<user_input>/<word>")
def rurdle_check_java_input(user_input, word):
    fillings = [0, 0, 0, 0, 0]  # 0 grey, 1 yellow, 2 green
    skip_letters = []
    print(user_input, word)

    if user_input == word:
        return [2, 2, 2, 2, 2]

    for i, x in enumerate(user_input):
        if x not in word:
            continue

        if word[i] == x:
            fillings[i] = 2
            skip_letters.append(x)
            continue

    for i, x in enumerate(user_input):
        if x in word and x not in skip_letters:
            fillings[i] = 1

    return jsonify(fillings)


if __name__ == '__main__':
    app.run()
