from openai import OpenAI
import requests

<<<<<<< HEAD
client = OpenAI(api_key="YOUR_API_KEY") 
sportradar_api_key = "YOUR_API_KEY"
=======
<<<<<<< HEAD
client = OpenAI(api_key="YOUR_API_KEY") 
sportradar_api_key = "YOUR_API_KEY"
=======
client = OpenAI(api_key="My_API_KEY") 
sportradar_api_key = "My_API_KEY"
>>>>>>> 988b9ad (Removed API keys)
>>>>>>> d096488 (Cleaned out repository and added projects without secrets)

team_names = []
team_IDs = []
previous_records = []

def get_teams_from_prompt(prompt):
    found_teams = []
    for market_team in team_names:
        market, team = market_team
        if team.lower() in prompt.lower() or market.lower() in prompt.lower():
            found_teams.append(f"{market} {team}")
        if len(found_teams) == 2:
            break
    return found_teams

def get_matchup(team1, team2):
    url = f"https://api.sportradar.us/nba/trial/v7/en/games/2024/REG/schedule.json?api_key={sportradar_api_key}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        for game in data['games']:
            if game['home']['name'].lower() == team1.lower() and game['away']['name'].lower() == team2.lower():
                home_team = game['home']['name']
                away_team = game['away'] ['name']

                return{
                    "home_team": game['home']['name'],
                    "away_team": game['away']['name']
                }
    return {
            "home_team": team1,
            "away_team": team2
            }
        


def get_teams():
    url = f"https://api.sportradar.us/nba/trial/v7/en/seasons/2023/REG/standings.json?api_key={sportradar_api_key}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        for conference in data['conferences']:
            for division in conference['divisions']:
                for team in division['teams']:
                    team_names.append((team['market'], team['name']))
                    team_IDs.append(f"{team['id']}")
                    previous_records.append({
                        "team_name": f"{team['market']} {team['name']}",
                        "win_pct": team['win_pct']
                    })

def get_last_10_results(team_name):
    last_10_results = []
    win_count = 0
    url = f"https://api.sportradar.us/nba/trial/v7/en/games/2023/REG/schedule.json?api_key={sportradar_api_key}"
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        for game in data['games']:
            if game['status'] == 'closed':
                if game['home']['name'].lower() == team_name.lower():
                    result = {
                    "opponent": game['away']['name'],
                    "home_away": "home",
                    "team_score": game['home_points'],
                    "opponent_score": game['away_points']
                    }
                    if game['home_points'] > game['away_points']:
                        win_count += 1
                elif game['away']['name'].lower() == team_name.lower():
                    result = {
                        "opponent": game['home']['name'],
                        "home_away": "away",
                        "team_score": game['away_points'],
                        "opponent_score": game['home_points']
                    }
                    if game['away_points'] > game['home_points']:
                        win_count += 1

                else:
                    continue

            last_10_results.append(result)

            if len(last_10_results) == 10:
                break
        
        return win_count / 10
    return 0
    
def get_team_win_pct(team_name):
    for record in previous_records:
        if record['team_name'] == team_name:
            return record['win_pct']
    return 0


    


def predict_winner(team1, team2):

    matchup = get_matchup(team1, team2)
    
    home_team = matchup['home_team']
    away_team = matchup['away_team']

    win_pct_team1 = get_team_win_pct(home_team)
    win_pct_team2 = get_team_win_pct(away_team)

    last_10_team1 = get_last_10_results(home_team)
    last_10_team2 = get_last_10_results(away_team)



    team1_stats = {
        'previous_record': win_pct_team1, 
        'home_team': True,        
        'current_form': last_10_team1        
    }
    
    team2_stats = {
        'previous_record': win_pct_team2,
        'home_team': False,
        'current_form': last_10_team2
    }

    
    previous_record_weight = 0.50
    home_advantage_weight = 0.15
    current_form_weight = 0.35

    
    team1_score = (
        team1_stats['previous_record'] * previous_record_weight +
        (1 if team1_stats['home_team'] else 0) * home_advantage_weight +
        (team1_stats['current_form'] / 10) * current_form_weight
    )

    team2_score = (
        team2_stats['previous_record'] * previous_record_weight +
        (1 if team2_stats['home_team'] else 0) * home_advantage_weight +
        (team2_stats['current_form'] / 10) * current_form_weight
    )

    if team1_score > team2_score:
        return team1
    else:
        return team2



def chat_with_bot(prompt):
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages = [{"role":  "user", "content": prompt}])

    return response.choices[0].message.content.strip()

def generate_custom_prompt(user_input, team1, team2, winner):
    f"""Generate a custom prompt based on the {user_input.lower()} and prediction."""
    prompt = (
        f"User asked about the match between {team1} and {team2} and must provide definitive answer."
        f"Mention which team is playing at home"
        f"Team {team1} has a win percentage of {get_team_win_pct(team1)* 100}, "
        f"while {team2} has a win percentage of {get_team_win_pct(team2)* 100}. "
        f"In their last 10 games, {team1} has won {get_last_10_results(team1)*10} of 10 "
        f"games, while {team2} has won {get_last_10_results(team2)*10} of 10 games."
        f"Use some of the stats above to back up why you picked {winner} in this patchup"
    )
    return prompt

if __name__ == "__main__":
    print("Chatbot: Ask me to predict a winner between 2 NBA teams")
    get_teams()
    while True:
        user_input = input("\nYou: ")
        if user_input.lower() in ["quit", "exit", "bye"]:
            break
            
        teams_found = get_teams_from_prompt(user_input.lower())
        if len(teams_found) == 2:

            team1 = teams_found[0]
            team2 = teams_found[1]
            
            winner = predict_winner(team1, team2)
            
            custom_prompt = generate_custom_prompt(user_input.lower(), team1, team2, winner)

            response = chat_with_bot(custom_prompt)
            print(f"\nChatbot: {response}")
        else:
            print("Could not find two teams in the prompt")